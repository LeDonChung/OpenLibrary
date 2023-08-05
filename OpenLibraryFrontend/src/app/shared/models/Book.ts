export class Book {
    id!: number;
    title?: string;
    isbn? :string;
    numberOfPages? :number;
    bookCover?: string;
    description?: string;
    rating?: number;
    publishDate?: Date;
    language? :string;
    is_activated?: boolean;
    is_deleted?: boolean;
    categories!: object[];
    authors!: object[];
    publisherId? :any;
    publisher?: any;
    constructor(title: string, isbn: string, numberOfPages: number, description: string,
        publishDate: Date, language: string, categories: any[], authors: any[], publisherId: any) {
            this.title = title;
            this.isbn = isbn;
            this.numberOfPages = numberOfPages;
            this.description = description;
            this.publishDate = publishDate;
            this.language = language;
            this.categories = categories;
            this.authors = authors;
            this.publisherId = publisherId;
        }
}