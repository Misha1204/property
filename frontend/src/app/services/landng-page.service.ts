import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, tap } from 'rxjs';
import { SectionPostRequest } from '../models/add-section.model';
import { Header } from '../models/header.model';
import { Section } from '../models/section.model';
import { UserInfo } from '../models/user-info.model';

@Injectable({
  providedIn: 'root',
})
export class LandingPageService {
  admin = {
    email: 'admin@admin.admin',
    password: '12345678',
  };

  constructor(private http: HttpClient) {}

  companyLogos = [
    {
      imageSrc: 'assets/company-logos/gcf.png',
    },
    {
      imageSrc: 'assets/company-logos/Fiabci.png',
    },
    {
      imageSrc: 'assets/company-logos/Bank_of_georgia.png',
    },
    {
      imageSrc: 'assets/company-logos/gcf.png',
    },
    {
      imageSrc: 'assets/company-logos/Fiabci.png',
    },
    {
      imageSrc: 'assets/company-logos/Bank_of_georgia.png',
    },
    {
      imageSrc: 'assets/company-logos/gcf.png',
    },
    {
      imageSrc: 'assets/company-logos/Fiabci.png',
    },
    {
      imageSrc: 'assets/company-logos/Bank_of_georgia.png',
    },
    {
      imageSrc: 'assets/company-logos/gcf.png',
    },
    {
      imageSrc: 'assets/company-logos/Fiabci.png',
    },
    {
      imageSrc: 'assets/company-logos/Bank_of_georgia.png',
    },
  ];

  // Auth
  login(request: { email: string; password: string }) {
    if (
      this.admin.email === request.email &&
      this.admin.password === request.password
    ) {
      localStorage.setItem('IS_USER_LOGGED_IN', 'TRUE');
      return true;
    }

    return false;
  }

  logout() {
    localStorage.clear();
  }

  // Header Methods
  getHeaderInfo() {
    return this.http.get<Header>(`http://localhost:8080/api/header`);
  }

  updateHeaderInfo(formData: any) {
    return this.http.put(`http://localhost:8080/api/header`, formData);
  }

  // Section Methods
  getPropertyInfo() {
    return this.http.get<Section[]>(`http://localhost:8080/api/property`);
  }

  getPropertyInfoById(id: number) {
    return this.http.get<Section>(`http://localhost:8080/api/property/${id}`);
  }

  addSectionInfo(formData: any) {
    return this.http.post(`http://localhost:8080/api/property`, formData);
  }

  editSection(sectionId: number, formData: any) {
    return this.http.put(
      `http://localhost:8080/api/property/${sectionId}`,
      formData
    );
  }

  deleteSection(sectionId: number) {
    return this.http.delete(`http://localhost:8080/api/property/${sectionId}`);
  }

  uploadImages(fd: any) {
    return this.http.post(
      `http://localhost:8080/api/property/uploadImages/4`,
      fd
    );
  }

  addUserInfo(request: UserInfo) {
    return this.http.post(`http://localhost:8080/api/subscriber`, request);
  }

  addHeaderInfo(formData: any) {
    return this.http.post(`http://localhost:8080/api/header`, formData);
  }

  // Company logos
  getCompanyLogos() {
    return this.http.get<
      {
        id: 0;
        link: 'string';
        image: 'string';
      }[]
    >(`http://localhost:8080/api/slider`);
  }

  addCompanyLogo(formData: any) {
    return this.http.post(`http://localhost:8080/api/slider`, formData);
  }

  deleteCompanyLogo(logoId: number) {
    return this.http.delete(`http://localhost:8080/api/slider/${logoId}`);
  }
}
