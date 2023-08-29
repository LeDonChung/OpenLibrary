import { Sorter } from "./Sorter";
import { User } from "./User";

export class Page<T> {

    length: number = 0;
    pageIndex: number = 0;
    pageSize:number = 12;
    dataSource: T[];
    sorter?: Sorter;
    constructor(length: number, pageIndex: number, pageSize: number, dataSource: T[], sorter?: Sorter) {
        this.length = length;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.dataSource = dataSource;
        this.sorter = sorter;
    }
}