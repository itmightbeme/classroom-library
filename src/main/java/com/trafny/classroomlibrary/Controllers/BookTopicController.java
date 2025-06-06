package com.trafny.classroomlibrary.Controllers;

import com.trafny.classroomlibrary.Entities.Topic;
import com.trafny.classroomlibrary.Repositories.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookTopicController {

    @Autowired
    private TopicRepo topicRepo;


    @GetMapping("/books/topics/list")
    public String listTopics(Model model) {
        List<Topic> topics = topicRepo.findAll();
        model.addAttribute("topics", topics);
        return "books/topics/list";
    }

    @GetMapping("/books/topics/new")
    public String showNewTopicForm(Model model) {
        model.addAttribute("topic", new Topic());
        return "books/topics/new";
    }

    @PostMapping("/books/topics")
    public String createTopic(@ModelAttribute Topic topic, RedirectAttributes ra) {
        topicRepo.save(topic);
        ra.addFlashAttribute("message", "Topic added successfully.");
        return "redirect:/books/topics/list";
    }

    @PostMapping("/books/topics/delete/{id}") //note: This function still needs to be added to UI page.
    public String deleteTopic(@PathVariable Long id, RedirectAttributes ra) {
        topicRepo.deleteById(id);
        ra.addFlashAttribute("message", "Topic deleted.");
        return "redirect:/books/topics/list";
    }













}
