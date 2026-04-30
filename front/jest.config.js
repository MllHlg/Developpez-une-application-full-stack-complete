module.exports = {
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  
  transform: {
    '^.+\\.(ts|js|mjs|html|svg)$': [
      'jest-preset-angular',
      {
        tsconfig: '<rootDir>/tsconfig.spec.json',
        stringifyContentPathRegex: '\\.(html|svg)$',
      },
    ],
  },
  transformIgnorePatterns: ['node_modules/(?!.*\\.mjs$)'],

  collectCoverage: true,
  collectCoverageFrom: [
    'src/app/**/*.ts'
  ],
  coverageDirectory: '<rootDir>/coverage/jest',
  coverageReporters: ['html', 'text-summary'],
  testPathIgnorePatterns: [
    '<rootDir>/node_modules/',
    '<rootDir>/dist/'
  ],
  coveragePathIgnorePatterns: [
    '/node_modules/',
    '.module.ts$',
    '.environment.ts$',
    "/src/app/app.routes.ts",
    "/src/app/app.config.ts",
    "/src/main.ts"
  ]
};