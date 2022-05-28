import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { tap } from 'rxjs';
import { LandingPageService } from 'src/app/services/landng-page.service';

@Component({
  selector: 'app-logos-section',
  templateUrl: './logos-section.component.html',
  styleUrls: ['./logos-section.component.scss'],
})
export class LogosSectionComponent implements OnInit {
  displayedColumns: string[] = ['id', 'link', 'actions'];
  dataSource!: MatTableDataSource<{
    id: 0;
    link: 'string';
    image: 'string';
  }>;

  constructor(private landingPageService: LandingPageService) {}

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.getCompanyLogos();
  }

  getCompanyLogos() {
    this.landingPageService
      .getCompanyLogos()
      .pipe(
        tap(res => {
          this.dataSource = new MatTableDataSource<{
            id: 0;
            link: 'string';
            image: 'string';
          }>(res);
          this.dataSource.paginator = this.paginator;
        })
      )
      .subscribe();
  }

  deleteSection(logoId: number) {
    this.landingPageService.deleteCompanyLogo(logoId).subscribe({
      next: () => {
        this.getCompanyLogos();
      },
    });
  }
}
