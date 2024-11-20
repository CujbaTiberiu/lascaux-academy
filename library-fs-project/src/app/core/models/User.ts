import {Review} from './Review';
import {Loan} from './Loan';

export interface User {
  id: number,
  name: string,
  surname: string,
  token: string,
  tokenExpireDate: Date,
  role: string
}
