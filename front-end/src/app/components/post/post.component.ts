import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {TimelineService} from '../../service/timeline.service';
import {ToastrService} from 'ngx-toastr';
import {AuthService} from '../../service/auth.service';
import {User} from '../../model/User';

@Component({
  selector: 'app-post',
  imports: [
    NgIf,
    FormsModule
  ],
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  user = JSON.parse(localStorage.getItem("user")!);
  imageUrl = `http://localhost:9090${this.user.profileImage}`;

  constructor(private timelineService: TimelineService, private toastr: ToastrService, private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(u => this.user = u);
  }


  text = '';
  selectedFile: File | null = null;
  previewUrl: string | null = null;
  isImage = false;
  isVideo = false;
  submitting = false;

  @Output() postCreated = new EventEmitter<void>();

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.selectedFile = file;

      const fileType = file.type;
      this.isImage = fileType.startsWith('image');
      this.isVideo = fileType.startsWith('video');

      const reader = new FileReader();
      reader.onload = () => {
        this.previewUrl = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  submitPost() {
    if (!this.text.trim() && !this.selectedFile) {
      console.error('Post content is empty.');
      return;
    }

    this.submitting = true;

    this.timelineService.createPost(this.text, this.selectedFile!).subscribe({
      next: (response) => {
        this.toastr.success('Post submitted successfully', 'Success');
        console.log('Post submitted successfully', response);
        this.resetForm();
        this.postCreated.emit();
      },
      error: (error) => {
        this.toastr.error('Error submitting post', 'Error');
        console.error('Error submitting post', error);
      },
      complete: () => {
        this.submitting = false;
      }
    });
  }


  resetForm() {
    this.text = '';
    this.selectedFile = null;
    this.previewUrl = null;
    this.isImage = false;
    this.isVideo = false;
  }

  onImageError(event: Event) {
    (event.target as HTMLImageElement).src = 'assets/images/unknown.png';
  }

}
