import { IPerson } from '@/shared/model/person.model';

export interface IMonthlyAvailability {
  id?: number;
  yearmonth?: Date | null;
  percent?: number | null;
  isActive?: boolean;
  person?: IPerson;
}

export class MonthlyAvailability implements IMonthlyAvailability {
  constructor(
    public id?: number,
    public yearmonth?: Date | null,
    public percent?: number | null,
    public isActive?: boolean,
    public person?: IPerson
  ) {
    this.isActive = this.isActive ?? false;
  }
}
