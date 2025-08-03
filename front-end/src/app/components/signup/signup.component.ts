import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthService} from '../../service/auth.service';
import {Router, RouterLink} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-signup',
  imports: [
    ReactiveFormsModule,
    NgIf,
    RouterLink,
    FormsModule,
    NgForOf
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
  constructor(private router: Router , private authService: AuthService,  private toastr: ToastrService) {}


  days: number[] = [];

  months = [
    { name: 'January', value: 1 },
    { name: 'February', value: 2 },
    { name: 'March', value: 3 },
    { name: 'April', value: 4 },
    { name: 'May', value: 5 },
    { name: 'june', value: 6 },
    { name: 'July', value: 7 },
    { name: 'August', value: 8 },
    { name: 'September', value: 9 },
    { name: 'October', value: 10 },
    { name: 'November', value: 11 },
    { name: 'December', value: 12 }
  ];
  years: number[] = [];

  selectedDay: number | null = null;
  selectedMonth: number | null = null;
  selectedYear: number | null = null;

  private fb = inject(FormBuilder);
  form: FormGroup = this.fb.group({
    name: [''],
    phoneNum: [''],
    birthDate: [''],
    email: [''],
    password: [''],
    gender: ['']
  });

  ngOnInit(): void {

    this.days = Array.from({ length: 31 }, (_, i) => i + 1);
    const currentYear = new Date().getFullYear();
    this.years = Array.from({ length: 100 }, (_, i) => currentYear - i);
    this.form = this.fb.group({
      name: [''],
      phoneNum: [''],
      birthDate: [''],
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

    this.updateBirthDate();

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

  updateBirthDate() {
    if (this.selectedDay && this.selectedMonth && this.selectedYear) {
      const birthDate = `${this.selectedYear}-${this.selectedMonth.toString().padStart(2, '0')}-${this.selectedDay.toString().padStart(2, '0')}`;
      this.form.get('birthDate')?.setValue(birthDate);
    }
  }


}
