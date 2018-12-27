Feature: This is to verify all operations using Rest Assured

  Scenario: Verify get operation for all posts
    Given I perform the get operation on the given url
    When The given url is "/posts"
    Then Verify the response and status code as 200

  Scenario: Request a particular post
    Given With the following details for post:
    | foo1  |
    | bar1  |
    | 1000  |
    When Make a post request to "/posts"
    Then Verify the response body and the status as 201

  Scenario: Put up a particular post
    Given With the following details for put:
    | 1  |
    | foo |
    | bar |
    | 1  |
    When Make a put request to "/posts/1"
    Then Verify the response body and the status as 200

  Scenario: Make a Patch request
    Given With the given value of title:
    | foo2  |
    When Make a patch request to "/posts/1"
    Then Verify the response body and the status as 200
    
  Scenario: Delete a particular post
    Given Perform a delete operation of a post
    When Delete the post with url "/posts/2"
    Then Verify the response and status code as 200

  Scenario Outline: Verify response lengths
    Given Perform a length check on the given url
    When if the url is "/<url>"
    Then Verify the length as "<length>"


    Examples:
    | url | length  |
    | posts | 100   |
    | comments | 500   |
    | albums | 100   |
    | photos | 5000   |
    | todos | 200   |
    | users | 10   |