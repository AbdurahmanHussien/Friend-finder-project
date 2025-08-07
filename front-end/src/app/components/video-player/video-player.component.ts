import {AfterViewInit, Component, ElementRef, Input, OnDestroy, ViewChild} from '@angular/core';
import videojs from 'video.js';
// @ts-ignore
import type { VideoJsPlayer } from 'video.js';

@Component({
  selector: 'app-video-player',
  imports: [],
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.css']
})
export class VideoPlayerComponent implements AfterViewInit, OnDestroy {


  @ViewChild('target', { static: true }) target!: ElementRef;

  @Input() src: string = '';

  player!: VideoJsPlayer;


  ngAfterViewInit(): void {
    this.player = videojs(this.target.nativeElement, {
      controls: true,
      autoplay: false,
      preload: 'auto',
      sources: [{
        src: this.src,
        type: 'video/mp4'
      }]
    });
  }

  ngOnDestroy(): void {
    if (this.player) {
      this.player.dispose();
    }
  }
}
