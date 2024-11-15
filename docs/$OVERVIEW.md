# ARC Prize information (as given by Google Gemini)

*My question*: how many tasks are contained in the 2024 @ARCprize contest?   Are there 100 "hidden" tasks?  If so how does a leaderboard indicate a fractional result instead of an integer?

*Gemini*: You're right to be curious about the scoring and task count in ARCprize! It's a bit more nuanced than a simple integer count.

Number of Tasks:

Public Leaderboard (ARC-AGI-Pub): This leaderboard uses a set of 400 publicly available ARC tasks. These are the same tasks used in the original ARC challenge.
Private Leaderboard (ARC-AGI): This is the main leaderboard for the ARCprize competition, and it uses a hidden set of 100 tasks. These tasks are designed to be more challenging and are kept secret to prevent overfitting and encourage generalization.
Fractional Scores on the Leaderboard:

The fractional scores you see on the leaderboard are due to how the ARC challenge is evaluated. Here's the breakdown:

Partial Credit: For each task, a solution can receive partial credit. Instead of just being right or wrong, the evaluation considers how many of the test cases within a task are solved correctly.
Averaging: The overall score is calculated by averaging the scores across all 100 hidden tasks. Since partial credit is possible, this average can result in a fractional score.
Example:

Let's say your AI solves 80 out of the 100 hidden tasks perfectly, and in the remaining 20 tasks, it gets half of the test cases correct. Your score would be calculated as:

(80 * 1) + (20 * 0.5) = 90

Then, 90 / 100 tasks = 0.9 or 90%

Why Hidden Tasks?

The hidden tasks are crucial for ensuring the fairness and integrity of the competition. They prevent participants from "gaming" the system by overfitting their solutions to the public tasks. This encourages the development of AI systems that can truly generalize and reason abstractly, which is the ultimate goal of the ARCprize.

*My question*: OK is more detail available.  Each ARC corpus Task has numerous examples and some have more than one "test".   Is there any indication about how many total "tests" are included in the "hidden" Tasks used in the Kaggle contest?

*Gemini*: Unfortunately, the exact number of total "tests" (or "test cases" as they are sometimes called) within the 100 hidden ARCprize tasks is not publicly available.

Here's why this information is kept confidential:

Prevent Overfitting: Knowing the precise number and distribution of test cases could allow participants to fine-tune their algorithms to perform well on specific types of tasks or patterns, potentially leading to overfitting and less generalization.
Maintain Challenge Integrity: The hidden tasks are designed to be a true test of an AI system's ability to reason and generalize. Revealing too much detail about their structure could compromise the challenge's difficulty and fairness.
What we do know:

Structure of ARC Tasks:
Each task consists of a small number of "demonstration" pairs (input and output grids) that illustrate the underlying rule or pattern.
Each task also includes one or more "test" pairs where the AI needs to apply the learned rule to a new input grid and predict the correct output.
Evaluation: The evaluation process considers how many of the test cases within each task are solved correctly. Partial credit is awarded for partially correct solutions.
Implications for your approach:

Focus on Generalization: Since you don't know the exact number of test cases or their specific characteristics, it's essential to focus on developing algorithms that can generalize well across different types of ARC tasks.
Robustness: Your algorithms should be robust to variations in the number of test cases, the complexity of the patterns, and the types of entities and relationships involved.
Exploration and Experimentation: Try different approaches to analyzing and categorizing the training tasks. Experiment with various algorithms and techniques to see what works best for generalizing to unseen problems.
Even without knowing the exact details of the hidden tasks, you can still make significant progress by focusing on understanding the core principles of abstract reasoning and developing AI systems that can learn and adapt effectively.