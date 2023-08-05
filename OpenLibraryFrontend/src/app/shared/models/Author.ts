export class Author {
    id! :number;
    fullName? :string;
    story? :string;
    is_activated?: boolean;
    is_deleted?: boolean;

    constructor(id: number, fullName: string, story: string, is_activated: boolean, is_deleted: boolean) {
        this.id = id;
        this.fullName = fullName;
        this.story = story;
        this.is_activated = is_activated;
        this.is_deleted = is_deleted;
    }
}