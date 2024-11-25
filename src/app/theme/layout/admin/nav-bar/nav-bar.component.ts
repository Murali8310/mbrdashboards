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

  

  // navCollapseForMobile() {
  //   const mobileCollapseElement = document.getElementById('mobileresponsiveElement');
    
  //   // Check if the element exists
  //   if (mobileCollapseElement) {
  //     // Toggle display between 'none' and 'block'
  //     if (mobileCollapseElement.style.display === 'none' || mobileCollapseElement.style.display === '') {
  //       mobileCollapseElement.style.display = 'block';
  //     } else {
  //       mobileCollapseElement.style.display = 'none';
  //     }
  //   }
  // }

  navCollapseForMobile() {
    const mobileCollapseElement = document.getElementById('mobileresponsiveElement');
    const mainContainer:any = document.querySelector('.pcoded-main-container');
    
    // Check if the element exists
    if (mobileCollapseElement && mainContainer) {
      // Toggle display between 'none' and 'block' for the navbar
      if (mobileCollapseElement.style.display === 'none' || mobileCollapseElement.style.display === '') {
        mobileCollapseElement.style.display = 'block';
        mainContainer.style.marginLeft = '70px'; // Set margin-left to 70px when navbar is visible
      } else {
        mobileCollapseElement.style.display = 'none';
        mainContainer.style.marginLeft = '0px'; // Set margin-left to 0px when navbar is hidden
      }
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
