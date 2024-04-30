import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../Services/authentication.service';
import { Router } from '@angular/router';
import { AuthResp } from '../../Types/AuthResp';
import { AccountService } from '../../Services/account.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  protected username: string = '';
  protected password: string = '';

  constructor(
    private authService: AuthenticationService,
    private accountService: AccountService,
    private router: Router
  ) {}
  ngOnInit(): void {
    if (this.authService.getAuthorizationToken() != null) {
      this.authService.checkAuthenticated().subscribe((result) => {
        if (result !== null && result) {
          this.router.navigate(['/assets']);
        }
      });
    }
  }
  onSubmit() {
    this.authService
      .Authenticate(this.username, this.password)
      .subscribe((resp) => {
        this.goToPage(resp);
      });
  }

  goToPage(result: AuthResp) {
    if (result.id != '') {
      this.authService.setAuthorizationToken(result.id);
      this.accountService.getAccountByName(this.username).subscribe(
        (result) => {
          if(result != undefined){
            this.accountService.setIdToken(result.id);
            this.router.navigate(['/assets']);
          }
          else{
            console.log(this.username)
            this.authService.clearAuthorizationToken();
            this.password = '';
          }

        }
      )
    } else {
      this.password = '';
    }
  }
}
