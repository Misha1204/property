import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { pipe, tap } from 'rxjs';
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

  constructor(
    private fb: FormBuilder,
    private landingPageService: LandingPageService,
    private route: ActivatedRoute
  ) {
    this.sectionId = <number | null>this.route.snapshot.paramMap.get('id');
  }

  ngOnInit(): void {
    if (!this.sectionId) {
      this.initForm();
    } else {
      this.landingPageService
        .getPropertyInfoById(this.sectionId)
        .pipe(
          tap(res => {
            this.section = res;
            this.initForm();
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
    let counter = 1;
    Object.getOwnPropertyNames(event.target.files).forEach(property => {
      if (property !== 'length') {
        this.formData.append(
          `image${counter}`,
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

    this.landingPageService.addSectionInfo(this.formData).subscribe();
  }
}
