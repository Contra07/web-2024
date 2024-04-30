import { Component } from '@angular/core';
import { Observable, of } from 'rxjs';
import { AuthenticationService } from '../../Services/authentication.service';
import { Router } from '@angular/router';
import { RouterLink, RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css',
})
export class MenuComponent {
  constructor(private service: AuthenticationService, private router: Router) {}
  isAuth(): boolean {
    return !(this.service.getAuthorizationToken() === null);
  }
  logout() {
    this.service.clearAuthorizationToken();
    this.router.navigate(['login'])
  }
}
