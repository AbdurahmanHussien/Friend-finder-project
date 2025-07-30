import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {CommonModule} from "@angular/common";
import {AuthService} from '../../service/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private fb = inject(FormBuilder);
  form: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
  });

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['loggedOut'] === 'true') {
        this.logOutMessage = 'You have been logged out successfully';
      }
    });
  }

  constructor(private router: Router,
              private route: ActivatedRoute,
              private loginService: AuthService,
              private toastr: ToastrService) {
  }

  errorMessage: String = '';
  showPassword = false;

  logOutMessage = '';

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  onSubmit() {
    if (this.form.invalid) return;

    const formValue = this.form.value;

    const payload = {
      ...formValue,
      email: formValue.email.trim().toLowerCase()
    };

    if (localStorage.getItem('jwt_token')) {
      this.router.navigate(['/']);
    }

    this.loginService.login(payload).subscribe({
      next: (res) => {
        const token = res.token;
        const refreshToken = res.refreshToken;
        localStorage.setItem('jwt_token', token);
        localStorage.setItem('refresh_token', refreshToken);
        localStorage.setItem("user", JSON.stringify(res.user));
        this.toastr.success('You have been logged in successfully', 'Welcome')
        this.router.navigate(['/'])
      },

      error: () => this.errorMessage = 'Invalid email or password'
    });
  }


  forgetPassword() {
    this.loginService.resetPassword({ email: this.form.value.email }).subscribe({
      next: () =>
        alert('Password reset to Hello@1234'),
      error: () =>
        alert('cannot reset password')
    });
  }
}
