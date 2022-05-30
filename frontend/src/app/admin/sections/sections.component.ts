import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { tap } from 'rxjs';
import { Section } from 'src/app/models/section.model';
import { LandingPageService } from 'src/app/services/landng-page.service';

@Component({
  selector: 'app-sections',
  templateUrl: './sections.component.html',
  styleUrls: ['./sections.component.scss'],
})
export class SectionsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'actions'];
  dataSource!: MatTableDataSource<Section>;

  constructor(private landingPageService: LandingPageService) {}

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngOnInit(): void {
    this.getSections();
  }

  getSections() {
    this.landingPageService
      .getAllProperties()
      .pipe(
        tap((res: any) => {
          this.dataSource = new MatTableDataSource<Section>(res);
          this.dataSource.paginator = this.paginator;
        })
      )
      .subscribe();
  }

  deleteSection(sectionId: number) {
    this.landingPageService.deleteSection(sectionId).subscribe({
      next: () => {
        this.getSections();
      },
    });
  }
}
