import { GraphQlModule } from './graph-ql.module';

describe('GraphQlModule', () => {
  let graphQlModule: GraphQlModule;

  beforeEach(() => {
    graphQlModule = new GraphQlModule();
  });

  it('should create an instance', () => {
    expect(graphQlModule).toBeTruthy();
  });
});
