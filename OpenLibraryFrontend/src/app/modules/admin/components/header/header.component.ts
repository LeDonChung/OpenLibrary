import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/shared/models/User';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit{
  @Output() toggleSidebarForMe: EventEmitter<any> = new EventEmitter();
  public userCurrent:string = "";
  constructor(
    private router: Router,
    private authService: AuthService) {}

  ngOnInit(): void {
    this.userCurrent = this.authService.getUserCurrent();
  }
  
  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  profile() {
    this.router.navigate(['/admin/profile']);
    
  }
  toggleSidebar() {
    this.toggleSidebarForMe.emit();
  }
}
