import { HttpEvent, HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { SessionService } from "../services/session";
import { inject } from "@angular/core";
import { Observable } from "rxjs";

export function customJwtInterceptorFn(request: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
  const sessionService = inject(SessionService);
  if (sessionService.isLogged) {
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${sessionService.sessionInformations!.token}`,
      },
    });
  }
  return next(request);
}