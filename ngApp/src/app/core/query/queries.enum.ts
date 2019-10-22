import gql from 'graphql-tag';
export enum Queries { 
    getAllArticles = gql`
        query listArticles{
            allArticles {
                id
                link
                added
                coverImage
                coverPosition
                title
                body
            }
        }
    `,
    getArticlesByLink = gql`
        query getArticleByLink($link: String!){
            articleByLink(articleLink: $link ) {
                id
                link
                added
                coverImage
                coverPosition
                title
                body
            }
        }
    `,

    getAllNews = gql`
        query getAllNews($skip: Int!, $limit: Int!) {
            getNews(skip: $skip, first: $limit){
                id
                added
                link
                coverImage
                title
                description
                body
                tags
            }
        }
    `,

    getNewsByLink = gql`
        query getNews($link: String!) {
            newsByLink(newsLink: $link){
                id
                added
                link
                coverImage
                title
                description
                body
                tags
            }
        }
    `,

    GET_NEWS_FOR_EDITOR = gql`
        query getNewsForEditor($news_id: Long!, $user_hash: String!){
            getNewsForEditor(newsId: $news_id, hash: $user_hash){
                id
                added
                link
                coverImage
                title
                description
                body
                tags
            }
        }
    `
}