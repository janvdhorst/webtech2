export class News {
  publishedOn: Date;
  headline: string;
  content: string;

  static fromObject(object: any): News {
    const n = new News();
    n.headline = object.headline;
    n.content = object.content;
    n.publishedOn = new Date(parseInt(object.publishedOn));
    return n;
  }
}
