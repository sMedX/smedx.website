import gql from 'graphql-tag';
export enum Mutations {
    messageMuttation = gql`
        mutation sendMessage($name: String, $email: String, $body: String){
            sendMessage(name: $name, email: $email, body: $body)
        }
    `,

    UPDATE_NEWS = gql`
        mutation updateNews($news: String, $user_hash: String){
            updateNews(news: $news, hash: $user_hash)
        }
    `
}