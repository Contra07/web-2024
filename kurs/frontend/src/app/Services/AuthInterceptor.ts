import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthenticationService } from "./authentication.service";
import { Injectable } from "@angular/core";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthenticationService) {}
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const authToken = this.auth.getAuthorizationToken();
    if (authToken !== null) {
      const authReq = req.clone({
        headers: req.headers.set('Authorization', `Basic ${authToken}`),
      });
      return next.handle(authReq);
    }
    return next.handle(req);
  }
}
