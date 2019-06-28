export class News {
  publishedOn: Date;
  headline: string;
  content: string;
  id: number;

  static fromObject(object: any): News {
	const n = new News();
	n.id = object.id;
    n.headline = object.headline;
    n.content = object.content;
    n.publishedOn = new Date(parseInt(object.publishedOn));
    return n;
  }
}
