export class Category {

    id!: number;
    name!: string;
    code!: string;
    is_activated?: boolean;
    is_deleted?: boolean;
    
    constructor(id: number, name: string, code: string, is_activated: boolean, is_deleted: boolean) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.is_activated = is_activated;
        this.is_deleted = is_deleted;
    }
}