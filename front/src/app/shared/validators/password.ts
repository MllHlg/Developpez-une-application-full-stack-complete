import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export function passwordValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const value = control.value;
        if (!value) return null;
        const hasUpperCase = /[A-Z]+/.test(value);
        const hasLowerCase = /[a-z]+/.test(value);
        const hasNumeric = /[0-9]+/.test(value);
        const hasSpecial = /[$@$!%*?&_-]+/.test(value);

        const passwordValidate = hasLowerCase && hasUpperCase && hasNumeric && hasSpecial;
        return !passwordValidate ? {passwordValid: true} : null;
    }
}