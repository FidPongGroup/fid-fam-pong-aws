# AWS IAM Templates

IAM is access management for AWS.  Basically the created of groups of and roles that allow for elements within and outside of AWS to access services

For example:

+ A webservice's role might be to read and write to an Aurora Database, or S3
+ a user might have the ability to create intrastructure in prod


At the moment the only roles we have are the user/developer roles which are at Admin level, we will eventually want to pair down to essential functionality


These will likely be the most difficult portion of our AWS configuration because they can be tricky.

[Click here to learn more about IAM](https://aws.amazon.com/iam/?nc2=h_m1)