import { Theme } from "./theme";

export interface User {
    id: number;
    email: string;
    username: string;
    themes: Theme[];
}