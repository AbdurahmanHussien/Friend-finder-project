package com.springboot.friend_finder.service.impl;

import com.springboot.friend_finder.constant.RequestStatus;
import com.springboot.friend_finder.dto.FriendshipDto;
import com.springboot.friend_finder.dto.authDto.UserDto;
import com.springboot.friend_finder.dto.authDto.UserPost;
import com.springboot.friend_finder.entity.Friendship;
import com.springboot.friend_finder.entity.auth.User;
import com.springboot.friend_finder.exceptions.BadRequestException;
import com.springboot.friend_finder.exceptions.ResourceNotFoundException;
import com.springboot.friend_finder.mapper.FriendshipMapper;
import com.springboot.friend_finder.mapper.UserMapper;
import com.springboot.friend_finder.repository.FriendshipRepository;
import com.springboot.friend_finder.repository.auth.UserRepository;
import com.springboot.friend_finder.service.IFriendService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService implements IFriendService {


	private final FriendshipRepository friendshipRepository;
	private final UserRepository userRepository;

	private final UserMapper userMapper;

	private final FriendshipMapper friendshipMapper;


	@Override
	public List<UserDto> getFriends(Long userId) {
		List<User> friends = friendshipRepository.findFriendsOfUser(userId);
		if (friends.isEmpty()) {
			throw new ResourceNotFoundException("no.friends");
		}
		return userMapper.toDtoList(friends);
	}

	@Override
	public FriendshipDto acceptFriendRequest(Long senderId, Long receiverId) {
		Optional<Friendship> existing = friendshipRepository.findBySenderAndReceiver(
				userRepository.findById(senderId).orElseThrow(() -> new BadRequestException("sender.notfound")),
				userRepository.findById(receiverId).orElseThrow(() -> new BadRequestException("receiver.notfound")));
		if (existing.isPresent()) {
			Friendship f = existing.get();

			if (f.getStatus().equals(RequestStatus.ACCEPTED)) {
				throw new BadRequestException("friendship.accepted");
			} else if (f.getStatus().equals(RequestStatus.REJECTED)) {
				throw new BadRequestException("friendship.rejected");
			}
			f.setStatus(RequestStatus.ACCEPTED);
			f.setRespondedAt(LocalDateTime.now());
			friendshipRepository.save(f);
			return friendshipMapper.toDto(friendshipRepository.save(f));

		}
		throw new BadRequestException("friendship.notfound");
	}


	@Override
	public FriendshipDto  sendFriendRequest(Long senderId, Long receiverId) {
		if (senderId.equals(receiverId)) {
			throw new BadRequestException("friendship.not.allowed");
		}

		User sender = userRepository.findById(senderId).orElseThrow(() -> new BadRequestException("sender.notfound"));
		User receiver = userRepository.findById(receiverId).orElseThrow(() -> new BadRequestException("receiver.notfound"));

		Optional<Friendship> existing = friendshipRepository.findExistingFriendshipBetween(sender, receiver);

		if (existing.isPresent()) {
			Friendship f = existing.get();
			if (f.getStatus().equals(RequestStatus.ACCEPTED)) {
				throw new BadRequestException("friendship.accepted");
			} else if (f.getStatus().equals(RequestStatus.PENDING)) {
				throw new BadRequestException("friendship.pending");
			}
		}

		Friendship newRequest = Friendship.builder()
								.sender(sender)
								.receiver(receiver)
								.status(RequestStatus.PENDING)
								.sentAt(LocalDateTime.now())
								.build();

		return friendshipMapper.toDto(friendshipRepository.save(newRequest));
	}

	@Override
	public FriendshipDto rejectFriendRequest(Long senderId, Long receiverId) {
		Optional<Friendship> existing = friendshipRepository.findBySenderAndReceiver(
				userRepository.findById(senderId).orElseThrow(() -> new BadRequestException("sender.notfound")),
				userRepository.findById(receiverId).orElseThrow(() -> new BadRequestException("receiver.notfound")));
		if (existing.isPresent()) {
			Friendship f = existing.get();
			f.setStatus(RequestStatus.REJECTED);
			f.setRespondedAt(LocalDateTime.now());
			return friendshipMapper.toDto(friendshipRepository.save(f));

		}
		throw new BadRequestException("friendship.notfound");
	}

	public List<FriendshipDto> getPendingRequests(Long userId) {
		List<Friendship> friendships = friendshipRepository.findPendingRequestsForUser(
				userRepository.findById(userId).orElseThrow(() -> new BadRequestException("user.notfound")));
		if (friendships.isEmpty()) {
			throw new BadRequestException("no.pending.requests");
		}
		return friendshipMapper.toDtoList(friendships);
	}

	public List<FriendshipDto> getSentRequests(Long userId) {

	List<Friendship> friendships =	friendshipRepository.findAllBySender(
				userRepository.findById(userId).orElseThrow(() -> new BadRequestException("user.notfound")));
	if (friendships.isEmpty()) {
		throw new BadRequestException("no.sent.requests");
	}
		return friendshipMapper.toDtoList(friendships);
	}

	@Transactional
	public void deleteRequest(Long senderId, Long receiverId) {
		Optional<Friendship> existing = friendshipRepository.findBySenderAndReceiver(
				userRepository.findById(senderId).orElseThrow(() -> new BadRequestException("sender.notfound")),
				userRepository.findById(receiverId).orElseThrow(() -> new BadRequestException("receiver.notfound"))
		);
		if (existing.isEmpty()) {
			throw new BadRequestException("friendship.notfound");
		}
		friendshipRepository.deleteFriendshipById(existing.get().getId());
	}

	@Override
	public List<UserPost> getSuggestions(Long userId) {
		Pageable topFive = PageRequest.of(0, 5);
		List<User> users = friendshipRepository.findSuggestedUsers(userId, topFive);
		if (users.isEmpty()) {
			throw new BadRequestException("no.suggestions");
		}
		return userMapper.userToUserPostList(users);
	}


    @Override
    public int getFriendCount(Long userId) {
        return friendshipRepository.countFriendsOfUser(userId);
    }
}
