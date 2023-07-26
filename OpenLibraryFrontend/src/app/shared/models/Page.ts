import { User } from "./User";

export class Page<T> {

    length: number = 0;
    pageIndex: number = 0;
    pageSize:number = 5;
    dataSource: T[];
    constructor(length: number, pageIndex: number, pageSize: number, dataSource: T[]) {
        this.length = length;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.dataSource = dataSource;
    }
}