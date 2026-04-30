import { FormControl } from '@angular/forms';
import { passwordValidator } from './password';

describe('Password Validator', () => {
  const validator = passwordValidator();

  it('should return null if value is empty', () => {
    const control = new FormControl('');
    expect(validator(control)).toBeNull();
  });

  it('should return null for a valid password', () => {
    const control = new FormControl('TEST_password_123');
    expect(validator(control)).toBeNull();
  });

  it('should return error if missing uppercase', () => {
    const control = new FormControl('password_123');
    expect(validator(control)).toEqual({ passwordValid: true });
  });

  it('should return error if missing lowercase', () => {
    const control = new FormControl('PASSWORD_123');
    expect(validator(control)).toEqual({ passwordValid: true });
  });

  it('should return error if missing number', () => {
    const control = new FormControl('TEST_password');
    expect(validator(control)).toEqual({ passwordValid: true });
  });

  it('should return error if missing special character', () => {
    const control = new FormControl('TESTpassword123');
    expect(validator(control)).toEqual({ passwordValid: true });
  });
});