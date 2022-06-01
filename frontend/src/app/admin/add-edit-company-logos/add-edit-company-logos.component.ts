import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { LandingPageService } from 'src/app/services/landng-page.service';

@Component({
  selector: 'app-add-edit-company-logos',
  templateUrl: './add-edit-company-logos.component.html',
  styleUrls: ['./add-edit-company-logos.component.scss'],
})
export class AddEditCompanyLogosComponent implements OnInit {
  form!: FormGroup;
  formData!: FormData;

  constructor(
    private landingPageService: LandingPageService,
    private location: Location,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.form = new FormGroup({
      link: new FormControl(''),
      image: new FormControl(''),
    });
  }

  onImageSelected(event: any) {
    this.formData = new FormData();
    this.formData.append(
      'image',
      event.target.files[0],
      event.target.files[0].name
    );
  }

  onUpload() {
    this.formData.append('link', this.form.get('link')?.value);
    this.landingPageService.addCompanyLogo(this.formData).subscribe({
      next: () => {
        this.formData = new FormData();
        this.toastr.success('ლოგო წარმატებით დაემატა');
        this.location.back();
      },
      error: err => {
        if (err.status === 200) {
          this.formData = new FormData();
          this.toastr.success('ლოგო წარმატებით დაემატა');
          this.location.back();
        }
      },
    });
  }
}
