import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LandingPageService } from '../services/landng-page.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss'],
})
export class AdminComponent implements OnInit {
  constructor(
    private landingPageService: LandingPageService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  logout() {
    this.landingPageService.logout();
    this.router.navigate(['/login']);
  }
}
