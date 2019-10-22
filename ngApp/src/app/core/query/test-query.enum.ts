import gql from "graphql-tag";
export enum TestQuery {
    testQuery = gql`
        query testQuery{
            test
        }
    `,
}