# How to contribute
We'd love to accept your patches and contributions to this project. There are a just a few small guidelines you need to follow.

# Prerequisites
Please follow instructions from README.MD and prepare your laptop in order to have installed all necessary software and of course modern version of your favorite Java IDE. Please make yourself familiar with [git workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow) as well.

# Submitting a patch
1. Follow the normal process of [forking the project](https://help.github.com/articles/fork-a-repo), and setup a new branch to work in. It's important that each group of changes be done in separate branches in order to ensure that a pull request only includes the commits related to that bug or feature.
2. Please [pick up an issue](http://bit.ly/2FX6rzj) of your choice and create a pull request with changes associated with it.
3. Any significant changes should almost always be accompanied by tests. The project already has good test coverage, so look at some of the existing tests if you're unsure how to go about it. Automatic code coverage checks will verify your pull request and put a failing status in a case of insufficient code coverage provided.
4. Files should be formatted according to [Google's java style guide](https://google.github.io/styleguide/javaguide.html). You can generate IDE settings out of `config/checkstyle/checkstyle.xml` config using checkstyle plugin.
- [IDEA Checkstyle plugin](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea/)
5. Do your best to have [well-formed commit messages](http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html) for each change. This provides consistency throughout the project, and ensures that commit messages are able to be formatted properly by various git tools.
6. Finally, push the commits to your fork and submit a [pull request](https://help.github.com/articles/creating-a-pull-request).
7. Make sure that you passed all checks including CI build and various quality checks introduced by open source cloud software integrated with that repo. You would also need at least one approval from core team members.

# Have a question?
Please join [Telegram group](https://t.me/vaadin_showcase_group) in order to reach development team and ask your question.
