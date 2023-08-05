export class Publisher {
    id!: number;
    name!: string;
    address!: string;
    story!: string;
    is_activated!: boolean;
    is_deleted!: boolean;
    constructor(id: number, name: string, address: string, story: string, is_activated: boolean, is_deleted: boolean) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.story = story;
        this.is_activated = is_activated;
        this.is_deleted = is_deleted
    }
}