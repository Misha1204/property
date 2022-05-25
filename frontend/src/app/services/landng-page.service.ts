import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SectionPostRequest } from '../models/add-section.model';
import { Header } from '../models/header.model';
import { Section } from '../models/section.model';
import { UserInfo } from '../models/user-info.model';

@Injectable({
  providedIn: 'root',
})
export class LandingPageService {
  constructor(private http: HttpClient) {}

  headerImages: { imageSrc: string }[] = [
    {
      imageSrc: 'assets/images/6016.jpg',
      // 'https://i.guim.co.uk/img/media/ac01822e1237b350779e9e41ab69c8bcc8d292ea/0_0_6016_3611/master/6016.jpg?width=1200&height=1200&quality=85&auto=format&fit=crop&s=e8ed1dcb5b915acb79446d4bf5202eac',
    },
    {
      imageSrc:
        'https://upload.wikimedia.org/wikipedia/commons/4/47/New_york_times_square-terabass.jpg',
    },
    {
      imageSrc:
        'https://cdn.britannica.com/08/187508-050-D6FB5173/Shanghai-Tower-Gensler-San-Francisco-world-Oriental-2015.jpg',
    },
    {
      imageSrc:
        'https://images.unsplash.com/photo-1460472178825-e5240623afd5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8YnVpbGRpbmdzfGVufDB8fDB8fA%3D%3D&w=1000&q=80',
    },
  ];

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
}
