// Angular Import
import { Component, EventEmitter, Output } from '@angular/core';
import { DashboardService } from 'src/app/dashboard.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent {
  // public props
  menuClass = false;
  collapseStyle = 'none';
  windowWidth = window.innerWidth;
  @Output() NavCollapse = new EventEmitter();
  @Output() NavCollapsedMob = new EventEmitter();

constructor(private dash:DashboardService){
this.navCollapseForMobile();
}

  // public method
  toggleMobOption() {
    this.menuClass = !this.menuClass;
    this.collapseStyle = this.menuClass ? 'block' : 'none';
    this.api()
  }

  navCollapse() {
    if (this.windowWidth >= 992) {
      this.NavCollapse.emit();
    }
  }

  navCollapseForMobile() {
    const mobileCollapseElement = document.getElementById('mobileresponsiveElement');
    // Check if the element exists
    if (mobileCollapseElement) {
      // Toggle the 'navbar-collapsed' class on the element
      mobileCollapseElement.classList.toggle('navbar-collapsed');
    }
  }

  api() {
    this.dash.getData().subscribe(
      (response) => {
        // this.data = response;
        // console.log('Data fetched successfully:', this.data);
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }
}
