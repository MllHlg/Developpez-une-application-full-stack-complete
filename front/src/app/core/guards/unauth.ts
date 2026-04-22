import { CanActivate, Router } from "@angular/router";
import { SessionService } from "../services/session";

export class UnauthGuard implements CanActivate {
    constructor(
        private router: Router,
        private sessionService: SessionService,
    ) {}

    public canActivate(): boolean {
        if (this.sessionService.isLogged) {
            this.router.navigate(['articles']);
            return false;
        }
        return true;
    }
}