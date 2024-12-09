// Angular Import
import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { jsPDF } from 'jspdf';
import html2canvas from 'html2canvas';
import { DashboardService } from './dashboard.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  // constructor
  constructor(private router: Router,
    private loginService: DashboardService,

  ) {}

  // life cycle event
  ngOnInit() {
    this.router.events.subscribe((evt) => {
      if (!(evt instanceof NavigationEnd)) {
        return;
      }

      const getElement:any = document.body;  // Access the body element directly
      getElement.style.zoom = '90%';  // Set zoom level to 80%
      window.scrollTo(0, 0);
    });
    // this.onLoginSubmit();

  }

  onLoginSubmit(): void {
    // if (this.loginForm.valid) {
      const login_id = '1234';
      const password = 'dddddd';

      // Call the login API in the service with the form values
      this.loginService.login(login_id, password).subscribe(
        (response) => {
          if (response && response.message === 'SUCCESS') {
            sessionStorage.setItem('user_id', response.user_id);
            sessionStorage.setItem('user_Name', response.user_Name);
            sessionStorage.setItem('email_id', response.email_id);
            sessionStorage.setItem('login_id', response.login_id);
            sessionStorage.setItem('role', response.role);
            sessionStorage.setItem('storeCode', response.storeCode);
            sessionStorage.setItem('accessRole', response.accessRole);
            sessionStorage.setItem('blockaccess', response.blockaccess);

            // Redirect to the dashboard or landing page
            this.router.navigate(['/landPage']);
          } else {
            // this.errorMessage = response.message || 'Login failed';
          }
        },
        (error) => {
          // this.errorMessage = 'An error occurred during login';
          console.error('Login error:', error);
        }
      );
    
  }


generatePDF() {

  const data = [
    { name: 'John Doe', age: 30, job: 'Engineer' },
    { name: 'Jane Smith', age: 25, job: 'Designer' },
    { name: 'Mark Taylor', age: 40, job: 'Manager' },
  ];

  const doc = new jsPDF('p', 'pt', 'a4');

  // Create a table with headers
  let content = `
    <table style="width: 100%; font-family: Arial, sans-serif; border-collapse: collapse;">
      <thead>
        <tr>
          <th style="border: 1px solid #dddddd; text-align: left; padding: 8px;">Name</th>
          <th style="border: 1px solid #dddddd; text-align: left; padding: 8px;">Age</th>
          <th style="border: 1px solid #dddddd; text-align: left; padding: 8px;">Job</th>
        </tr>
      </thead>
      <tbody>
  `;

  // Append data rows dynamically
  data.forEach(item => {
    content += `
      <tr>
        <td style="border: 1px solid #dddddd; text-align: left; padding: 8px;">${item.name}</td>
        <td style="border: 1px solid #dddddd; text-align: left; padding: 8px;">${item.age}</td>
        <td style="border: 1px solid #dddddd; text-align: left; padding: 8px;">${item.job}</td>
      </tr>
    `;
  });

  content += `
      </tbody>
    </table>
  `;

  // Create an invisible HTML element to render the styled content
  const tempElement = document.createElement('div');
  tempElement.innerHTML = content;
  document.body.appendChild(tempElement);

  // Convert the HTML content to an image using html2canvas
  html2canvas(tempElement).then(canvas => {
    const imgData = canvas.toDataURL('image/png');
    const imgWidth = doc.internal.pageSize.getWidth();
    const imgHeight = canvas.height * imgWidth / canvas.width;

    // Add image to PDF
    doc.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
    doc.save('styled-data.pdf');

    // Clean up the temporary HTML element
    document.body.removeChild(tempElement);
  });
}


generatePDF1() {
  const issues = [
    { description: 'Insecure Direct Object Reference (IDOR) in user profile page', priority: 'medium' },
    { description: 'Cross-Site Scripting (XSS) vulnerability in user input forms', priority: 'high' },
    { description: 'Sensitive Data Exposure in user account settings', priority: 'high' },
    { description: 'Insecure Direct Object Reference (IDOR) in document download functionality', priority: 'medium' },
    { description: 'Missing HTTP Security Headers in API responses', priority: 'low' },
    { description: 'SQL Injection vulnerability in user search functionality', priority: 'high' },
    { description: 'Cross-Site Request Forgery (CSRF) protection missing', priority: 'medium' }
  ];

  const doc = new jsPDF();

  // Set title
  const title = 'Burp Scanner Report';
  const titleYPosition = 10; // Position for the title
  const titleHeight = 15; // Height for the title background

  // Set title background color
  doc.setFillColor(0, 0, 255); // Blue color
  doc.rect(0, titleYPosition - 5, doc.internal.pageSize.width, titleHeight, 'F'); // Draw background rectangle

  // Set title text style
  doc.setFont('helvetica', 'bold');
  doc.setTextColor(255, 255, 255); // White text for title
  doc.text(title, 15, titleYPosition); // Title text

  // Initialize yPosition for issues
  let yPosition = titleYPosition + titleHeight + 10; // Start below the title
  const lineHeight = 10; // Height between lines

  if (issues.length === 0) {
    doc.setFont('helvetica', 'normal');
    doc.setTextColor(0, 0, 0); // Black color for text
    doc.text("No issues found.", 15, yPosition);
  } else {
    issues.forEach((issue, index) => {
      // Set issue number and description as bold
      doc.setFont('helvetica', 'bold'); // Set font to bold
      doc.setTextColor(0, 0, 0); // Black color for text

      // Split the description into multiple lines if needed
      const lines = doc.splitTextToSize(`${index + 1}. ${issue.description}`, 85); // Adjust width as needed
      const maxHeight = lines.length * lineHeight;

      // Add the issue description text
      doc.text(lines, 15, yPosition); // Issue description

      // Set background color for priority
      let bgColor:any;
      if (issue.priority === 'high') {
        bgColor = 'red';
      } else if (issue.priority === 'medium') {
        bgColor = 'yellow';
      } else if (issue.priority === 'low') {
        bgColor = 'green';
      }

      // Add the priority text over the background
      const priorityXPosition = 105; // X position for priority text
      const priorityYPosition = yPosition + (maxHeight / 2) - 5; // Center the priority vertically

      // Draw background rectangle for priority
      doc.setFillColor(bgColor);
      doc.rect(priorityXPosition - 2, priorityYPosition, 50, 15, 'F'); // Rectangle for priority background

      // Set text color for priority
      doc.setFont('helvetica', 'normal'); // Set font back to normal
      if (bgColor === 'red' || bgColor === 'green') {
        doc.setTextColor(255, 255, 255); // White text for darker backgrounds
      } else {
        doc.setTextColor(0, 0, 0); // Black text for yellow
      }

      // Add priority text
      doc.text(issue.priority.charAt(0).toUpperCase() + issue.priority.slice(1), priorityXPosition, priorityYPosition + 10); // Priority text

      // Move to the next line
      yPosition += maxHeight + 10; // Adjust position for the next issue (adding space)

      // Check if we need a new page
      if (yPosition > 280) { // A4 page height limit
        doc.addPage();
        yPosition = 20; // Reset yPosition for the new page
      }
    });
  }

  // Save the PDF
  doc.save('styled-issues.pdf');
}



}
