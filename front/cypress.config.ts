import { defineConfig } from 'cypress';
import coverageTask from '@cypress/code-coverage/task';

export default defineConfig({
  
  e2e: {
    setupNodeEvents(on, config) {
      coverageTask(on, config);
      return config;
    },
    'baseUrl': 'http://localhost:4200'
  },
  
  
  component: {
    devServer: {
      framework: 'angular',
      bundler: 'webpack',
    },
    specPattern: '**/*.cy.ts'
  }
  
})