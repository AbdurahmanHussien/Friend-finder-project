import { Component, OnInit } from '@angular/core';


import {FormsModule} from '@angular/forms';
import {Router} from '@angular/router';
import {ContactRequest} from '../../model/contactRequest';
import {ContactService} from '../../service/contact.service';

@Component({
  selector: 'app-contact-info',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './contact-info.component.html',
  styleUrls: ['./contact-info.component.css']
})
export class ContactInfoComponent implements OnInit {

  constructor( private _contactService: ContactService) {}

  ngOnInit(): void {}

    errors: any[]= [];
    successMessage: string = 'Your message has been sent successfully';
    success: boolean | undefined ;

   contactData: ContactRequest = {
    name: '',
    email: '',
    subject: '',
    message: '',
  };

  onSubmit() {
    this._contactService.sendMessage(this.contactData).subscribe({
      next: () => {
        this.success = true;
      },
      error: (error) => {
        this.success = false;
        if(Array.isArray(error.error)) {
          this.errors = this.handleErrors(error.error);
        }
      }
    });
  }



  handleErrors(errors: any[]) {
    const englishMessages = errors.map(err => err.messages?.message_en).filter(msg => !!msg);
    console.log(englishMessages);
    return englishMessages;
  }


}
