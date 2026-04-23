import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { SessionService } from "../services/session";

export const authGuard: CanActivateFn = () => {
    const router = inject(Router);
    const sessionService = inject(SessionService);

    if (!sessionService.isLogged) {
        router.navigate(['login']);
        return false;
    }
    return true;
}