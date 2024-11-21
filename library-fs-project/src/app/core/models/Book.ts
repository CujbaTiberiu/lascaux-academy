import {Review} from './Review';
import {Author} from './Author';

export interface Book {
  id: number;
  title: string;
  publicationDate: Date,
  categories: string[],
  authors: Author[],
  review: Review[],
  voteAvg: number,
}
