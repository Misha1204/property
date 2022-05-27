import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroupDirective,
  NgForm,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LandingPageService } from 'src/app/services/landng-page.service';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
}

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss'],
})
export class AuthComponent implements OnInit {
  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  passwordFormControl = new FormControl('', [Validators.required]);
  matcher = new MyErrorStateMatcher();

  formData = new FormData();

  constructor(
    private fb: FormBuilder,
    private landingPageService: LandingPageService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {}

  onLogin() {
    this.formData.append('username', this.emailFormControl.value);
    this.formData.append('password', this.passwordFormControl.value);
    this.landingPageService.login(this.formData).subscribe({
      next: res => {
        if (res) {
          localStorage.setItem('IS_USER_LOGGED_IN', 'TRUE');
          this.router.navigate(['/admin/add_header']);
          this.formData = new FormData();
        } else {
          this.toastr.error('მომხმარებლის ელ-ფოსტა ან პაროლი არასწორია');
          this.formData = new FormData();
        }
      },
    });
  }
}
