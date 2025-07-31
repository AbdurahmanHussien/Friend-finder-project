import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from '../../service/auth.service';
import {Router, RouterLink} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-signup',
  imports: [
    ReactiveFormsModule,
    NgIf,
    RouterLink
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  constructor(private router: Router , private authService: AuthService,  private toastr: ToastrService) {}


  private fb = inject(FormBuilder);
  form: FormGroup = this.fb.group({
    name: [''],
    phoneNum: [''],
    age: [''],
    email: [''],
    password: [''],
    gender: ['']
  });

  ngOnInit(): void {
    this.form = this.fb.group({
      name: [''],
      phoneNum: [''],
      age: [''],
      email: [''],
      password: [''],
      gender: ['']
    });

    this.form.get('email')?.valueChanges.subscribe(value => {
      if (value) {
        const clean = value.trim().toLowerCase();
        if (clean !== value) {
          this.form.get('email')?.setValue(clean, { emitEvent: false });
        }
      }
    });
  }

  showPassword = false


  signupErrors : any = {}

  errorMessageEmail: string = ''


  onSubmit() {
    if (this.form.invalid) return;

    const formValue = this.form.value;

    const payload = {
      ...formValue,
      email: formValue.email.trim().toLowerCase()
    };

    this.authService.signup(payload).subscribe({
      next: (res) => {
        const token = res.token;
        const refreshToken = res.refreshToken;
        localStorage.setItem('jwt_token', token);
        localStorage.setItem('refresh_token', refreshToken);
        localStorage.setItem("user", JSON.stringify(res.user));
        this.toastr.success('You have been logged in successfully', 'Welcome')
        this.router.navigate(['/'])
      },
      error: (err) => {
        // debugger
        this.errorMessageEmail = err.error?.messages?.message_en || '' ;

        this.signupErrors = {};
        err.error.forEach((err: any) => {

          this.signupErrors[err.field] = err.messages.message_en;
        });

      }
    });
  }

  togglePassword() {
    this.showPassword = !this.showPassword
  }
}
