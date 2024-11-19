import {Review} from './Review';
import {Loan} from './Loan';

export interface User {
  id: number,
  name: string,
  surname: string,
  token: string,
  tokenExpireDate: Date,
  roles: string,
  loans: Loan[],
  reviews: Review[]
}
