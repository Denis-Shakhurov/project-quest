@import dto.GamePage
@param GamePage page

@template.layout.page(
content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>${page.getGame().getName()}</h1>
            <p>${page.getGame().getDescription()}: ${page.getQuestion()}</p>
            <form action="/games/${page.getGame().getId()}" method="post" class="rss-form text-body">
                <div class="row">
                    <div class="col">
                        <div class="form-floating">
                            <input id="name-input" autofocus="" type="text" required="" name="answer" aria-label="name"
                                   class="form-control w-50" placeholder="введи ответ" autocomplete="off">
                            <label for="name-input">Введи ответ</label>
                        </div>
                    </div>
                    <div class="col">
                        <button type="submit" class="h-100 btn btn-lg btn-primary px-sm-3">Проверить</button>
                    </div>
                    <p>${page.getCurrentAnswer()} ${page.getAnswer()} ${page.getFlash()}</p>
                </div>
            </form>
        </div>
    </section>
`
)