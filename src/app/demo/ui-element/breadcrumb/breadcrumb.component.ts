import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from 'src/app/dashboard.service';

@Component({
  selector: 'app-breadcrumb',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.scss']
})
export default class BreadcrumbComponent {
  constructor(public dashservice:DashboardService){

  }
}