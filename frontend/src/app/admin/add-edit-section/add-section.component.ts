import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { map } from 'rxjs';
import { Section } from 'src/app/models/section.model';
import { LandingPageService } from '../../services/landng-page.service';

@Component({
  selector: 'app-add-section',
  templateUrl: './add-section.component.html',
  styleUrls: ['./add-section.component.scss'],
})
export class AddSectionComponent implements OnInit {
  form!: FormGroup;
  formData = new FormData();

  sectionId!: number | null;
  section!: Section;

  deletedImageIds: number[] = [];
  deletedFileIds: number[] = [];

  constructor(
    private fb: FormBuilder,
    private landingPageService: LandingPageService,
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private toastr: ToastrService
  ) {
    this.sectionId = <number | null>this.route.snapshot.paramMap.get('id');
  }

  ngOnInit(): void {
    this.getSections();
  }

  getSections() {
    if (!this.sectionId) {
      this.initForm();
    } else if (this.sectionId) {
      this.landingPageService
        .getPropertyInfoById(this.sectionId)
        .pipe(
          map(res => {
            for (let i = 0; i < res.images.length; i++) {
              res.images[i] = { id: i + 1, image: res.images[i] };
            }

            this.formData.append(`fileAddress1`, res.file);
            this.section = res;
            this.initForm();

            return res;
          })
        )
        .subscribe();
    }
  }

  initForm() {
    this.form = this.fb.group({
      name: this.fb.control(this.section ? this.section.name : ''),
      title: this.fb.control(this.section ? this.section.title : ''),
      city: this.fb.control(this.section ? this.section.city : ''),
      country: this.fb.control(this.section ? this.section.country : ''),
      description: this.fb.control(
        this.section ? this.section.description : ''
      ),
      nameEng: this.fb.control(this.section ? this.section.nameEng : ''),
      titleEng: this.fb.control(this.section ? this.section.titleEng : ''),
      cityEng: this.fb.control(this.section ? this.section.cityEng : ''),
      countryEng: this.fb.control(this.section ? this.section.countryEng : ''),
      descriptionEng: this.fb.control(
        this.section ? this.section.descriptionEng : ''
      ),
    });
  }

  onImageSelected(event: any) {
    let counter = 0;
    Object.getOwnPropertyNames(event.target.files).forEach(property => {
      if (property !== 'length') {
        this.formData.append(
          `image${
            this.sectionId ? this.deletedImageIds[counter] : counter + 1
          }`,
          event.target.files[+property],
          event.target.files[+property].name
        );
        counter++;
      }
    });
  }

  onPdfSelected(event: any) {
    this.formData.append(
      'file',
      event.target.files[0],
      event.target.files[0].name
    );
  }

  onCreateSection() {
    Object.keys(this.form.value).forEach(key =>
      this.formData.append(key, this.form.value[key])
    );

    if (this.section && this.section.images.length > 0) {
      this.section.images.forEach(image => {
        this.formData.append(`imageAddress${image.id}`, image.image);
      });
    }

    if (!this.sectionId) {
      this.landingPageService.addSectionInfo(this.formData).subscribe({
        next: () => {
          this.toastr.success('სექცია წარმატებით დაემატა');
          this.location.back();
        },
        error: () => {
          this.router.navigate(['../']);
          this.location.back();
        },
      });
    } else {
      this.landingPageService
        .editSection(this.sectionId, this.formData)
        .subscribe({
          next: () => {
            this.toastr.success('სექცია წარმატებით შეიცვალა');
            this.location.back();
          },
        });
    }
  }

  deleteImage(imageId: number) {
    this.section.images = this.section.images.filter(res => res.id !== imageId);
    this.deletedImageIds.push(imageId);
  }

  deleteFile() {
    this.section.file = '';
  }

  resetData() {
    this.form.reset();
    this.formData = new FormData();
    this.deletedImageIds = [];
    this.getSections();
  }
}
