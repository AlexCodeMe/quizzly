// package com.alexcasey.quizzly.service;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// import com.alexcasey.quizzly.enums.QuestionTypeEnum;
// import com.alexcasey.quizzly.mapper.QuestionMapper;
// import com.alexcasey.quizzly.model.Question;
// import com.alexcasey.quizzly.model.User;
// import com.alexcasey.quizzly.utils.Utils;
// import com.alexcasey.quizzly.dto.QuestionDto;
// import com.alexcasey.quizzly.dto.QuizDto;

// @Slf4j
// @RequiredArgsConstructor
// @Service
// public class AiService {

//     @Value("${openai.api.key}")
//     private String openaiApiKey;

//     private final QuestionService questionService;
//     private final QuestionMapper questionMapper;
//     private final Utils utils;

//     public String generateQuiz(
//             String name,
//             String description,
//             String topic,
//             QuestionTypeEnum questionType,
//             int numberOfQuestions) {

//         String apiUrl = "https://api.openai.com/v1/chat/completions";

//         Map<String, Object> requestBody = new HashMap<>();
//         requestBody.put("model", "gpt-4o-mini");
//         requestBody.put("temperature", 0.1);

//         List<Map<String, Object>> messages = new ArrayList<>();
//         messages.add(Map.of(
//                 "role", "system",
//                 "content",
//                 "You are a helpful assistant that generates quiz questions based on the topic " + topic
//                         + " and description " + description
//                         + " provided. The quiz should be composed of " + numberOfQuestions
//                         + " questions with the only type " + questionType + " questions."
//                         + " The questions should include: "
//                         + "1. the Question, "
//                         + "2. the Answer, "
//                         + "3. the Explanation, "
//                         + "4.if the question type is MULTIPLE_CHOICE, include the Options. if the question type is FILL_IN_THE_BLANK, include the blank to fill in. For SHORT_ANSWER, provide a generic sample answer."
//                         + "Multile choice questions should have between 3 to 8 options."));

//         requestBody.put("messages", messages);
//         requestBody.put("max_tokens", 16000);

//         HttpHeaders headers = new HttpHeaders();
//         headers.set("Authorization", "Bearer " + openaiApiKey);
//         headers.setContentType(MediaType.APPLICATION_JSON);

//         HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

//         RestTemplate restTemplate = new RestTemplate();
//         ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

//         return response.getBody();
//     }

//     public QuizDto parseAiResponse(String aiResponse, String name, String topic, String description,
//             QuestionTypeEnum questionType) {
//         User user = utils.getCurrentUser();

//         // Parsing AI response
//         String contentMarker = "\"content\":";
//         int startIndex = aiResponse.indexOf(contentMarker) + contentMarker.length();
//         int endIndex = aiResponse.lastIndexOf("}");
//         String content = aiResponse.substring(startIndex, endIndex).trim();
//         content = content.replaceAll("^\"|\"$", "").replaceAll("\\\\n", "\n").replaceAll("\\\\\"", "\"");
//         log.info("Content: {}", content);

//         // Splitting questions based on a consistent marker
//         String[] questionsArray = content.split("\\n\\d+\\. \\*\\*Question:\\*\\*");

//         // Build the QuizDto
//         QuizDto quizDto = QuizDto.builder()
//                 .name(name)
//                 .topic(topic)
//                 .description(description)
//                 .questionIds(new HashSet<>())
//                 .userId(user.getId())
//                 .build();

//         List<QuestionDto> questionDtos = new ArrayList<>();

//         for (String questionBlock : questionsArray) {
//             if (questionBlock.trim().isEmpty()) {
//                 continue;
//             }

//             log.info("Parsed Question Block: {}", questionBlock);

//             QuestionDto questionDto = null;

//             if (questionBlock.contains("Options:")) {
//                 questionDto = parseMultipleChoiceQuestion(questionBlock);
//             } else if (questionBlock.contains("______")) {
//                 questionDto = parseFillInTheBlankQuestion(questionBlock);
//             } else if (questionBlock.contains("True") || questionBlock.contains("False")) {
//                 questionDto = parseTrueFalseQuestion(questionBlock);
//             } else {
//                 questionDto = parseShortAnswerQuestion(questionBlock);
//             }

//             if (questionDto != null) {
//                 Question questionEntity = questionMapper.toEntity(questionDto);
//                 Question savedQuestion = questionService.save(questionEntity);

//                 quizDto.getQuestionIds().add(savedQuestion.getId());
//                 questionDtos.add(questionDto);
//             }
//         }

//         return quizDto;
//     }

//     private QuestionDto parseMultipleChoiceQuestion(String questionBlock) {
//         log.info("Parsing multiple choice block: {}", questionBlock);
//         String question = extractPattern(questionBlock,
//                 "\\*\\*Question:\\*\\*(.*?)\\n");
//         log.info("QUESTION: {}", question);
//         String answer = extractPattern(questionBlock, "\\*\\*Answer:\\*\\*(.*?)\\n");
//         String explanation = extractPattern(questionBlock, "\\*\\*Explanation:\\*\\*(.*?)\\n");

//         // Extract multiple-choice options (A), (B), (C), etc.
//         Set<String> options = new HashSet<>();
//         Pattern optionPattern = Pattern.compile("\\*\\*Options:\\*\\*(.*?)\\n(.*?)\\n(.*?)\\n(.*?)\\n", Pattern.DOTALL);
//         Matcher optionMatcher = optionPattern.matcher(questionBlock);
//         if (optionMatcher.find()) {
//             options.add(optionMatcher.group(1).trim());
//             options.add(optionMatcher.group(2).trim());
//             options.add(optionMatcher.group(3).trim());
//             options.add(optionMatcher.group(4).trim());
//         }

//         return QuestionDto.builder()
//                 .question(question)
//                 .answer(answer)
//                 .explanation(explanation)
//                 .options(options) // Set the options for multiple-choice
//                 .questionType(QuestionTypeEnum.MULTIPLE_CHOICE)
//                 .build();
//     }

//     private QuestionDto parseTrueFalseQuestion(String questionBlock) {
//         // Extract question, answer (True/False), and explanation
//         String question = extractPattern(questionBlock,
//                 "\\*\\*Question:\\*\\*(.*?)\\n");
//         // String question = questionBlock.substring(0,
//         // questionBlock.indexOf("**Answer:**")).trim();
//         String answer = extractPattern(questionBlock, "\\*\\*Answer:\\*\\*(.*?)\\n");
//         String explanation = extractPattern(questionBlock, "\\*\\*Explanation:\\*\\*(.*?)\\n");

//         return QuestionDto.builder()
//                 .question(question)
//                 .answer(answer)
//                 .explanation(explanation)
//                 .questionType(QuestionTypeEnum.TRUE_FALSE)
//                 .build();
//     }

//     private QuestionDto parseFillInTheBlankQuestion(String questionBlock) {
//         String question = extractPattern(questionBlock,
//                 "\\*\\*Question:\\*\\*(.*?)\\n");
//         // String question = questionBlock.substring(0,
//         // questionBlock.indexOf("**Answer:**")).trim();
//         String answer = extractPattern(questionBlock, "\\*\\*Answer:\\*\\*(.*?)\\n");
//         String explanation = extractPattern(questionBlock, "\\*\\*Explanation:\\*\\*(.*?)\\n");

//         return QuestionDto.builder()
//                 .question(question)
//                 .answer(answer)
//                 .explanation(explanation)
//                 .questionType(QuestionTypeEnum.FILL_IN_THE_BLANK)
//                 .build();
//     }

//     private QuestionDto parseShortAnswerQuestion(String questionBlock) {
//         String question = extractPattern(questionBlock,
//                 "\\*\\*Question:\\*\\*(.*?)\\n");
//         // String question = questionBlock.substring(0,
//         // questionBlock.indexOf("**Answer:**")).trim();
//         String answer = extractPattern(questionBlock, "\\*\\*Answer:\\*\\*(.*?)\\n");
//         String explanation = extractPattern(questionBlock, "\\*\\*Explanation:\\*\\*(.*?)\\n");

//         return QuestionDto.builder()
//                 .question(question)
//                 .answer(answer)
//                 .explanation(explanation)
//                 .questionType(QuestionTypeEnum.SHORT_ANSWER)
//                 .build();
//     }

//     // Helper method to extract patterns from the AI response
//     private String extractPattern(String text, String regex) {
//         Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
//         Matcher matcher = pattern.matcher(text);
//         if (matcher.find()) {
//             return matcher.group(1).trim();
//         }
//         return "";
//     }
// }
