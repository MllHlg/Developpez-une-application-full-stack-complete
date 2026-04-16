import { Article } from "./article";
import { Commentaire } from "./commentaire";

export interface ArticleDetail extends Article {
    theme: string;
    commentaires?: Commentaire[];
}