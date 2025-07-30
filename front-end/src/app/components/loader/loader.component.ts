import { Component } from '@angular/core';
import {NgIf} from '@angular/common';
import {LoaderService} from '../../service/loader.service';

@Component({
  selector: "loader",
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.css']
})
export class LoaderComponent {

  showLoader = false;

  constructor(private _loaderService: LoaderService) {
    this._loaderService.isLoading.subscribe(
      (value) => (this.showLoader = value)
    )

  }

}
